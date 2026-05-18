import { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { Button } from '../components/ui/button.jsx'

export default function FundDetails() {
  const { id } = useParams()
  const [fund, setFund] = useState(null)
  const [comments, setComments] = useState([])
  const [loading, setLoading] = useState(false)

  useEffect(() => {
    if (id) fetchFund()
  }, [id])

  const fetchFund = async () => {
    setLoading(true)
    try {
      const res = await fetch(`/api/v1/fund/${id}`)
      const json = await res.json()
      const data = json?.data || json
      setFund(data)

      const cRes = await fetch(`/api/v1/comment/by-fund/${id}`)
      const cJson = await cRes.json()
      const cData = cJson?.data || cJson
      setComments(cData?.data || cData || [])
    } catch (e) {
      console.error(e)
    } finally {
      setLoading(false)
    }
  }

  if (loading) return <div>Loading...</div>
  if (!fund) return <div>Fund not found</div>

  return (
    <div>
      <h1>{fund.title}</h1>
      <div style={{ display: 'flex', gap: 20 }}>
        <div style={{ flex: 2 }}>
          {fund.images && fund.images.length > 0 && (
            <img src={fund.images[0]} alt="cover" style={{ width: '100%', borderRadius: 12 }} />
          )}
          <h3>Story</h3>
          <p>{fund.description}</p>

          <h3>Beneficiary / location</h3>
          <p>{fund.location}</p>

          <h3>Creator</h3>
          <p>{fund.user?.username || fund.user?.name}</p>

          <h3>Comments</h3>
          {comments.length === 0 ? <p>No comments yet.</p> : (
            <ul>
              {comments.map((c) => (
                <li key={c.id}>{c.comment}</li>
              ))}
            </ul>
          )}
        </div>

        <aside style={{ flex: 1 }}>
          <div className="card">
            <h3>Goal</h3>
            <p>{fund.amount}</p>
            <h3>Raised</h3>
            <p>{fund.collectedAmount || 0}</p>
            <div style={{ marginTop: 12 }}>
              <Button>Donate</Button>
            </div>
          </div>
        </aside>
      </div>
    </div>
  )
}
