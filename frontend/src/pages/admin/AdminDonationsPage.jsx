import { useEffect, useMemo, useState } from 'react'
import { Button } from '../../components/ui/button.jsx'
import { Card } from '../../components/ui/card.jsx'

const getAuthHeaders = () => {
  const token = localStorage.getItem('adminToken')
  return token ? { Authorization: `Bearer ${token}` } : {}
}

export default function AdminDonationsPage() {
  const [donations, setDonations] = useState([])
  const [search, setSearch] = useState('')
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')

  useEffect(() => {
    async function loadDonations() {
      try {
        setLoading(true)
        setError('')
        const res = await fetch('/api/v1/donation/all?page=1&limit=100', {
          headers: getAuthHeaders(),
        })
        const result = await res.json()
        if (!res.ok) {
          throw new Error(result.message || 'Failed to load donations')
        }
        setDonations(result.data?.data || result.data?.list || result.data || [])
      } catch (err) {
        setError(err.message)
      } finally {
        setLoading(false)
      }
    }

    loadDonations()
  }, [])

  const filteredDonations = useMemo(() => {
    if (!search) return donations
    return donations.filter((donation) => {
      return [donation.name, donation.email, donation.phoneNumber, donation.message, donation.target]
        .filter(Boolean)
        .some((field) => field.toLowerCase().includes(search.toLowerCase()))
    })
  }, [donations, search])

  return (
    <div className="space-y-6">
      <div className="dashboard-header">
        <div>
          <h1>Donation Audit</h1>
          <p>Review all donation records and audit gift details.</p>
        </div>
        <div className="dashboard-metrics">Total donations: {donations.length}</div>
      </div>

      <Card>
        <div className="admin-search-row">
          <input
            className="input"
            placeholder="Search donations by donor, target, or message"
            value={search}
            onChange={(event) => setSearch(event.target.value)}
          />
        </div>
        {error ? <p className="text-error">{error}</p> : null}
        {loading ? (
          <p className="text-slate-300">Loading donations…</p>
        ) : (
          <div className="admin-table-wrap">
            <table className="admin-table">
              <thead>
                <tr>
                  <th>Donor</th>
                  <th>Amount</th>
                  <th>Target</th>
                  <th>Message</th>
                  <th>Date</th>
                </tr>
              </thead>
              <tbody>
                {filteredDonations.map((donation) => (
                  <tr key={donation.id || `${donation.name}-${donation.amount}-${donation.target}`}>
                    <td>{donation.name || 'Anonymous'}</td>
                    <td>{donation.amount ?? '—'}</td>
                    <td>{donation.target || '—'}</td>
                    <td>{donation.message || '—'}</td>
                    <td>{donation.createdAt ? new Date(donation.createdAt).toLocaleDateString() : '-'}</td>
                  </tr>
                ))}
              </tbody>
            </table>
            {filteredDonations.length === 0 ? <p className="text-slate-300">No donations found.</p> : null}
          </div>
        )}
      </Card>
    </div>
  )
}
