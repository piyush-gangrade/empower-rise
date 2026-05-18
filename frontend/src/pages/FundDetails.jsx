import { useEffect, useState } from 'react'
import { useParams, Link } from 'react-router-dom'

export default function FundDetails() {
  const { id } = useParams()
  const [fund, setFund] = useState(null)
  const [comments, setComments] = useState([])
  const [loading, setLoading] = useState(false)

  const [commentText, setCommentText] = useState('')
  const [editingId, setEditingId] = useState(null)
  const [editingText, setEditingText] = useState('')
  const [error, setError] = useState('')

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

      const cRes = await fetch(`/api/v1/comment/by-fund/${id}?page=1&limit=100`)
      const cJson = await cRes.json()
      const cData = cJson?.data || cJson
      // comment service may return nested data
      const list = cData?.data || cData || []
      setComments(list)
    } catch (e) {
      console.error(e)
    } finally {
      setLoading(false)
    }
  }

  const getMe = () => {
    try {
      return JSON.parse(localStorage.getItem('authUser') || '{}')
    } catch (e) {
      return {}
    }
  }

  const getToken = () => localStorage.getItem('authToken') || ''

  const handleCreateComment = async (e) => {
    e.preventDefault()
    setError('')
    const me = getMe()
    if (!me?.id) return setError('Please login to comment')
    if (!commentText.trim()) return setError('Please enter a comment')
    try {
      const fd = new FormData()
      fd.append('fundId', id)
      fd.append('text', commentText.trim())
      fd.append('userId', me.id)

      const res = await fetch('/api/v1/comment/create', {
        method: 'POST',
        headers: getToken() ? { Authorization: `Bearer ${getToken()}` } : {},
        body: fd,
      })
      const json = await res.json()
      if (!res.ok) throw new Error(json?.message || 'Failed to post comment')
      setCommentText('')
      fetchFund()
    } catch (err) {
      setError(err.message)
    }
  }

  const startEdit = (c) => {
    setEditingId(c.id)
    setEditingText(c.text || c.comment || '')
  }

  const cancelEdit = () => {
    setEditingId(null)
    setEditingText('')
  }

  const handleUpdateComment = async (e) => {
    e.preventDefault()
    setError('')
    if (!editingText.trim()) return setError('Please enter comment text')
    try {
      const fd = new FormData()
      fd.append('text', editingText.trim())
      const res = await fetch(`/api/v1/comment/update/${editingId}`, {
        method: 'PATCH',
        headers: getToken() ? { Authorization: `Bearer ${getToken()}` } : {},
        body: fd,
      })
      const json = await res.json()
      if (!res.ok) throw new Error(json?.message || 'Failed to update comment')
      cancelEdit()
      fetchFund()
    } catch (err) {
      setError(err.message)
    }
  }

  const handleDeleteComment = async (commentId) => {
    setError('')
    if (!confirm('Delete this comment?')) return
    try {
      const res = await fetch(`/api/v1/comment/delete/${commentId}`, {
        method: 'POST',
        headers: getToken() ? { Authorization: `Bearer ${getToken()}` } : {},
      })
      const json = await res.json()
      if (!res.ok) throw new Error(json?.message || 'Failed to delete comment')
      fetchFund()
    } catch (err) {
      setError(err.message)
    }
  }

  if (loading) return <div>Loading...</div>
  if (!fund) return <div>Fund not found</div>

  const me = getMe()

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
          <div style={{ marginBottom: 12 }}>
            {error && <div className="alert alert-error">{error}</div>}
            <form onSubmit={handleCreateComment} className="space-y-2">
              <textarea
                placeholder="Add encouragement, question or update..."
                value={commentText}
                onChange={(e) => setCommentText(e.target.value)}
                rows={3}
                className="input"
              />
              <div style={{ display: 'flex', gap: 8, alignItems: 'center' }}>
                <button className="button" type="submit">Comment</button>
                {!me?.id && <Link to="/login">Sign in to comment</Link>}
              </div>
            </form>
          </div>

          {comments.length === 0 ? <p>No comments yet.</p> : (
            <ul style={{ listStyle: 'none', padding: 0 }}>
              {comments.map((c) => (
                <Comment
                  key={c.id}
                  comment={c}
                  me={me}
                  editingId={editingId}
                  editingText={editingText}
                  setEditingText={setEditingText}
                  startEdit={startEdit}
                  cancelEdit={cancelEdit}
                  onSave={handleUpdateComment}
                  onDelete={handleDeleteComment}
                />
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
              <Link className="button" to={`/funds/${fund.id}/donate`}>Donate</Link>
            </div>
          </div>
        </aside>
      </div>
    </div>
  )
}
