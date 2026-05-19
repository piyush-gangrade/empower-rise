import { useEffect, useMemo, useState } from 'react'
import { Link } from 'react-router-dom'
import { Button } from '../../components/ui/button.jsx'
import { Card } from '../../components/ui/card.jsx'

const getAuthHeaders = () => {
  const token = localStorage.getItem('adminToken')
  return token ? { Authorization: `Bearer ${token}` } : {}
}

export default function AdminCampaignsPage() {
  const [campaigns, setCampaigns] = useState([])
  const [search, setSearch] = useState('')
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')

  useEffect(() => {
    async function loadCampaigns() {
      try {
        setLoading(true)
        setError('')
        const res = await fetch('/api/v1/fund/all?page=1&limit=100', {
          headers: getAuthHeaders(),
        })
        const result = await res.json()
        if (!res.ok) {
          throw new Error(result.message || 'Failed to load campaigns')
        }
        setCampaigns(result.data?.data || result.data?.list || result.data || [])
      } catch (err) {
        setError(err.message)
      } finally {
        setLoading(false)
      }
    }

    loadCampaigns()
  }, [])

  const filteredCampaigns = useMemo(() => {
    if (!search) return campaigns
    return campaigns.filter((item) => {
      return [item.title, item.description, item.location, item.category?.name]
        .filter(Boolean)
        .some((field) => field.toLowerCase().includes(search.toLowerCase()))
    })
  }, [campaigns, search])

  const deleteCampaign = async (id) => {
    if (!window.confirm('Remove this campaign?')) return
    try {
      setError('')
      const res = await fetch(`/api/v1/fund/${id}`, {
        method: 'DELETE',
        headers: getAuthHeaders(),
      })
      const result = await res.json()
      if (!res.ok) {
        throw new Error(result.message || 'Unable to delete campaign')
      }
      setCampaigns((current) => current.filter((item) => item.id !== id))
    } catch (err) {
      setError(err.message)
    }
  }

  return (
    <div className="space-y-6">
      <div className="dashboard-header">
        <div>
          <h1>Campaign Management</h1>
          <p>Review all fundraisers, remove fake campaigns, and edit campaign details.</p>
        </div>
        <div className="dashboard-metrics">Total campaigns: {campaigns.length}</div>
      </div>

      <Card>
        <div className="admin-search-row">
          <input
            className="input"
            placeholder="Search campaigns by title, location, or category"
            value={search}
            onChange={(event) => setSearch(event.target.value)}
          />
        </div>
        {error ? <p className="text-error">{error}</p> : null}
        {loading ? (
          <p className="text-slate-300">Loading campaigns…</p>
        ) : (
          <div className="admin-table-wrap">
            <table className="admin-table">
              <thead>
                <tr>
                  <th>Title</th>
                  <th>Category</th>
                  <th>Goal</th>
                  <th>Raised</th>
                  <th>Location</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {filteredCampaigns.map((fund) => (
                  <tr key={fund.id}>
                    <td>{fund.title || fund.name || 'Untitled'}</td>
                    <td>{fund.category?.name || '—'}</td>
                    <td>{fund.amount ?? fund.goal ?? '—'}</td>
                    <td>{fund.amountCollected ?? fund.oldAmountCollected ?? '—'}</td>
                    <td>{fund.location || '-'}</td>
                    <td className="admin-actions-cell">
                      <Button size="sm" variant="secondary" as={Link} to={`/admin/campaigns/edit/${fund.id}`}>
                        Edit
                      </Button>
                      <Button size="sm" variant="ghost" onClick={() => deleteCampaign(fund.id)}>
                        Remove
                      </Button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
            {filteredCampaigns.length === 0 ? <p className="text-slate-300">No campaigns found.</p> : null}
          </div>
        )}
      </Card>
    </div>
  )
}
