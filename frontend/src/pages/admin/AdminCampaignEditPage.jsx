import { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { Button } from '../../components/ui/button.jsx'
import { Card } from '../../components/ui/card.jsx'
import { Input } from '../../components/ui/input.jsx'

const getAuthHeaders = () => {
  const token = localStorage.getItem('adminToken')
  return token ? { Authorization: `Bearer ${token}` } : {}
}

export default function AdminCampaignEditPage() {
  const { id } = useParams()
  const navigate = useNavigate()
  const [title, setTitle] = useState('')
  const [description, setDescription] = useState('')
  const [amount, setAmount] = useState('')
  const [dayLeft, setDayLeft] = useState('')
  const [location, setLocation] = useState('')
  const [categoryId, setCategoryId] = useState('')
  const [categories, setCategories] = useState([])
  const [images, setImages] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')

  useEffect(() => {
    async function loadCategories() {
      try {
        const res = await fetch('/api/category')
        const result = await res.json()
        setCategories(result.data || result || [])
      } catch (err) {
        console.error(err)
      }
    }

    async function loadCampaign() {
      try {
        const res = await fetch(`/api/v1/fund/${id}`, {
          headers: getAuthHeaders(),
        })
        const result = await res.json()
        if (!res.ok) throw new Error(result.message || 'Unable to load campaign')
        const fund = result.data
        setTitle(fund.title || fund.name || '')
        setDescription(fund.description || '')
        setAmount(fund.amount ?? fund.goal ?? '')
        if (fund.dayLeft) {
          const formattedDate = new Date(fund.dayLeft).toISOString().split('T')[0];
          setDayLeft(formattedDate);
        } else {
          setDayLeft('');
        }
        setLocation(fund.location || '')
        setCategoryId(fund.category?.id || '')
      } catch (err) {
        setError(err.message)
      }
    }

    loadCategories()
    loadCampaign()
  }, [id])

  const handleImagesChange = (event) => {
    setImages(Array.from(event.target.files))
  }

  const handleSubmit = async (event) => {
    event.preventDefault()
    setError('')
    setLoading(true)

    try {
      const formData = new FormData()
      formData.append('title', title)
      formData.append('description', description)
      formData.append('amount', amount)
      formData.append('dayLeft', dayLeft)
      formData.append('location', location)
      formData.append('categoryId', categoryId)
      images.forEach((file) => formData.append('images', file))

      const res = await fetch(`/api/v1/fund/update/${id}`, {
        method: 'PATCH',
        headers: getAuthHeaders(),
        body: formData,
      })
      const result = await res.json()
      if (!res.ok) {
        throw new Error(result.message || 'Unable to update campaign')
      }
      navigate('/admin/campaigns')
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <Card title="Edit Campaign" description="Update campaign details, goals, or category.">
      <form className="space-y-6" onSubmit={handleSubmit}>
        <div>
          <label className="mb-2 block text-sm font-medium text-slate-200" htmlFor="title">
            Title
          </label>
          <Input id="title" value={title} onChange={(event) => setTitle(event.target.value)} required />
        </div>
        <div>
          <label className="mb-2 block text-sm font-medium text-slate-200" htmlFor="description">
            Description
          </label>
          <textarea
            id="description"
            value={description}
            onChange={(event) => setDescription(event.target.value)}
            rows="5"
            className="textarea"
            required
          />
        </div>
        <div className="form-grid">
          <div>
            <label className="mb-2 block text-sm font-medium text-slate-200" htmlFor="amount">
              Goal amount
            </label>
            <Input id="amount" value={amount} onChange={(event) => setAmount(event.target.value)} required />
          </div>
          <div>
            <label className="mb-2 block text-sm font-medium text-slate-200" htmlFor="dayLeft">
              Days left
            </label>
            {/* Added type="date" here */}
            <Input id="dayLeft" type="date" value={dayLeft} onChange={(event) => setDayLeft(event.target.value)} />
          </div>
        </div>
        <div className="form-grid">
          <div>
            <label className="mb-2 block text-sm font-medium text-slate-200" htmlFor="location">
              Location
            </label>
            <Input id="location" value={location} onChange={(event) => setLocation(event.target.value)} />
          </div>
          <div>
            <label className="mb-2 block text-sm font-medium text-slate-200" htmlFor="category">
              Category
            </label>
            <select
              id="category"
              value={categoryId}
              onChange={(event) => setCategoryId(event.target.value)}
              className="input"
            >
              <option value="">Select a category</option>
              {categories.map((category) => (
                <option key={category.id} value={category.id}>
                  {category.name}
                </option>
              ))}
            </select>
          </div>
        </div>
        <div>
          <label className="mb-2 block text-sm font-medium text-slate-200" htmlFor="images">
            Upload new images
          </label>
          <Input id="images" type="file" accept="image/*" multiple onChange={handleImagesChange} />
        </div>
        {error ? <p className="text-error">{error}</p> : null}
        <div className="form-actions">
          <Button type="submit" disabled={loading}>
            {loading ? 'Saving…' : 'Save changes'}
          </Button>
        </div>
      </form>
    </Card>
  )
}
