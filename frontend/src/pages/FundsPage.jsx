import { useEffect, useState } from 'react'
import FundCard from '../components/FundCard'

export default function FundsPage() {
  const [funds, setFunds] = useState([])
  const [categories, setCategories] = useState([])
  const [search, setSearch] = useState('')
  const [category, setCategory] = useState('')
  const [page, setPage] = useState(1)
  const [limit] = useState(9)
  const [totalPages, setTotalPages] = useState(1)
  const [sort, setSort] = useState('newest')
  const [loading, setLoading] = useState(false)

  useEffect(() => {
    fetchCategories()
  }, [])

  useEffect(() => {
    fetchFunds()
  }, [page, category])

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

  const fetchFunds = async () => {
    setLoading(true)
    try {
      let url = `/api/v1/fund/all?page=${page}&limit=${limit}`
      if (category) {
        url = `/api/v1/fund/all-by-category/${category}?page=${page}&limit=${limit}`
      }
      const res = await fetch(url)
      const json = await res.json()
      const payload = json?.data || json
      const data = payload?.data || payload
      console.log(data)
      setTotalPages(payload?.totalPage || 1)
      setFunds(data || [])
    } catch (e) {
      console.error(e)
    } finally {
      setLoading(false)
    }
  }

  const filtered = funds
    .filter((f) => !search || (f.title || f.name || '').toLowerCase().includes(search.toLowerCase()))
    .sort((a, b) => {
      if (sort === 'mostFunded') return (b.collectedAmount || 0) - (a.collectedAmount || 0)
      if (sort === 'endingSoon') return new Date(a.dayLeft) - new Date(b.dayLeft)
      return new Date(b.date || b.createdAt || Date.now()) - new Date(a.date || a.createdAt || Date.now())
    })

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-3xl font-semibold text-white">Explore Funds</h1>
          <p className="mt-2 text-sm text-slate-400">Discover campaigns to support.</p>
        </div>
        <div style={{ display: 'flex', gap: 8 }}>
          <input className="input" placeholder="Search funds" value={search} onChange={(e) => setSearch(e.target.value)} />
          <select className="input" value={category} onChange={(e) => { setCategory(e.target.value); setPage(1) }}>
            <option value="">All categories</option>
            {categories.map((c) => (
              <option key={c.id} value={c.id}>{c.name}</option>
            ))}
          </select>
          <select className="input" value={sort} onChange={(e) => setSort(e.target.value)}>
            <option value="newest">Newest</option>
            <option value="mostFunded">Most funded</option>
            <option value="endingSoon">Ending soon</option>
          </select>
        </div>
      </div>

      {loading ? <div>Loading...</div> : (
        <div className="card-grid">
          {filtered.map((f) => <FundCard key={f.id} fund={f} />)}
        </div>
      )}

      <div style={{ display: 'flex', justifyContent: 'center', gap: 8 }}>
        <button className="button" onClick={() => setPage((p) => Math.max(1, p - 1))} disabled={page <= 1}>Prev</button>
        <span style={{ alignSelf: 'center' }}>{page} / {totalPages}</span>
        <button className="button" onClick={() => setPage((p) => Math.min(totalPages, p + 1))} disabled={page >= totalPages}>Next</button>
      </div>
    </div>
  )
}
