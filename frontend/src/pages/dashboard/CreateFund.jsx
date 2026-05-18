import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { Button } from '../../components/ui/button.jsx'

export default function CreateFund() {
  const [title, setTitle] = useState('')
  const [amount, setAmount] = useState('')
  const [description, setDescription] = useState('')
  const [categoryId, setCategoryId] = useState('')
  const [dayLeft, setDayLeft] = useState('')
  const [location, setLocation] = useState('')
  const [images, setImages] = useState([])
  const [categories, setCategories] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')
  const navigate = useNavigate()

  useEffect(() => {
    fetchCategories()
  }, [])

  const fetchCategories = async () => {
    try {
      const res = await fetch('/api/category')
      const json = await res.json()
      const data = json?.data || json
      setCategories(data || [])
    } catch (e) {
      console.error(e)
    }
  }

  const handleFiles = (e) => {
    setImages(Array.from(e.target.files))
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError('')
    setLoading(true)

    const me = JSON.parse(localStorage.getItem('authUser') || '{}')
    if (!me?.id) {
      setError('Not authenticated')
      setLoading(false)
      return
    }

    try {
      const form = new FormData()
      form.append('title', title)
      form.append('amount', Number(amount))
      images.forEach((file) => form.append('images', file))
      form.append('description', description)
      form.append('userId', me.id)
      form.append('categoryId', categoryId)
      form.append('dayLeft', dayLeft)
      form.append('location', location)

      const token = localStorage.getItem('authToken')

      const res = await fetch('/api/v1/fund/create', {
        method: 'POST',
        headers: token ? { Authorization: `Bearer ${token}` } : {},
        body: form,
      })

      const json = await res.json()
      if (!res.ok) throw new Error(json?.message || 'Failed to create fund')

      const created = json.data || json
      navigate(`/funds/${created.id}`)
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div>
      <h2>Create Fundraiser</h2>
      {error && <div className="alert alert-error">{error}</div>}
      <form className="space-y-4" onSubmit={handleSubmit}>
        <div>
          <label className="mb-2 block text-sm font-medium text-slate-200">Fund title</label>
          <input className="input" value={title} onChange={(e) => setTitle(e.target.value)} required />
        </div>

        <div>
          <label className="mb-2 block text-sm font-medium text-slate-200">Goal amount</label>
          <input className="input" type="number" value={amount} onChange={(e) => setAmount(e.target.value)} required />
        </div>

        <div>
          <label className="mb-2 block text-sm font-medium text-slate-200">Category</label>
          <select className="input" value={categoryId} onChange={(e) => setCategoryId(e.target.value)} required>
            <option value="">Select category</option>
            {categories.map((c) => (
              <option key={c.id} value={c.id}>{c.name}</option>
            ))}
          </select>
        </div>

        <div>
          <label className="mb-2 block text-sm font-medium text-slate-200">Cover images</label>
          <input className="input" type="file" multiple accept="image/*" onChange={handleFiles} />
        </div>

        <div>
          <label className="mb-2 block text-sm font-medium text-slate-200">Story / description</label>
          <textarea className="input" rows={6} value={description} onChange={(e) => setDescription(e.target.value)} required />
        </div>

        <div>
          <label className="mb-2 block text-sm font-medium text-slate-200">Beneficiary / location</label>
          <input className="input" value={location} onChange={(e) => setLocation(e.target.value)} required />
        </div>

        <div>
          <label className="mb-2 block text-sm font-medium text-slate-200">End date</label>
          <input className="input" type="date" value={dayLeft} onChange={(e) => setDayLeft(e.target.value)} required />
        </div>

        <Button type="submit" disabled={loading}>{loading ? 'Creating...' : 'Create campaign'}</Button>
      </form>
    </div>
  )
}
