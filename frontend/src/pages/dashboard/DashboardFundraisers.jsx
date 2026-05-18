import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import FundCard from '../../components/FundCard'

export default function DashboardFundraisers() {
  const [funds, setFunds] = useState([])
  const [loading, setLoading] = useState(false)

  useEffect(() => {
    fetchMyFunds()
  }, [])

  const fetchMyFunds = async () => {
    setLoading(true)
    try {
      const res = await fetch('/api/v1/fund/all?page=1&limit=100')
      const json = await res.json()
      const data = json?.data?.data || json?.data || []
      const me = JSON.parse(localStorage.getItem('authUser') || '{}')
      const myFunds = (data || []).filter((f) => f.user?.id === me.id)
      setFunds(myFunds)
    } catch (e) {
      console.error(e)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <h2>My Fundraisers</h2>
        <Link to="create-fund" className="button">
          Create fundraiser
        </Link>
      </div>

      {loading ? (
        <div>Loading...</div>
      ) : funds.length === 0 ? (
        <p>You haven't created any fundraisers yet.</p>
      ) : (
        <div className="card-grid">
          {funds.map((f) => (
            <FundCard key={f.id} fund={f} />
          ))}
        </div>
      )}
    </div>
  )
}
