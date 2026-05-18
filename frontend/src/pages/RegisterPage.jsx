import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { Button } from '../components/ui/button.jsx'
import { Card } from '../components/ui/card.jsx'
import { Input } from '../components/ui/input.jsx'

export default function RegisterPage() {
  const [name, setName] = useState('')
  const [email, setEmail] = useState('')
  const [phoneNumber, setPhoneNumber] = useState('')
  const [password, setPassword] = useState('')
  const [photoURL, setPhotoURL] = useState('')
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)
  const navigate = useNavigate()

  const handleSubmit = async (event) => {
    event.preventDefault()
    setError('')
    setLoading(true)

    try {
      const payload = {
        name,
        email,
        phoneNumber,
        password,
      }

      if (photoURL.trim()) {
        payload.photoURL = photoURL.trim()
      }

      const response = await fetch('/api/v1/User', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(payload),
      })

      const data = await response.json()

      if (!response.ok) {
        throw new Error(data?.message || 'Failed to create account')
      }

      navigate('/login', {
        state: { successMessage: 'Account created successfully. Please login.' },
      })
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <Card title="Register" description="Create a new account to start fundraising or donate.">
      <form className="space-y-4" onSubmit={handleSubmit}>
        {error ? <div className="alert alert-error">{error}</div> : null}
        <div>
          <label className="mb-2 block text-sm font-medium text-slate-200" htmlFor="name">
            Name
          </label>
          <Input
            id="name"
            placeholder="Jane Doe"
            value={name}
            onChange={(event) => setName(event.target.value)}
            required
          />
        </div>
        <div>
          <label className="mb-2 block text-sm font-medium text-slate-200" htmlFor="email">
            Email
          </label>
          <Input
            id="email"
            type="email"
            placeholder="you@example.com"
            value={email}
            onChange={(event) => setEmail(event.target.value)}
            required
          />
        </div>
        <div>
          <label className="mb-2 block text-sm font-medium text-slate-200" htmlFor="phoneNumber">
            Phone
          </label>
          <Input
            id="phoneNumber"
            placeholder="01XXXXXXXXX"
            value={phoneNumber}
            onChange={(event) => setPhoneNumber(event.target.value)}
            required
          />
        </div>
        <div>
          <label className="mb-2 block text-sm font-medium text-slate-200" htmlFor="password">
            Password
          </label>
          <Input
            id="password"
            type="password"
            placeholder="Create a password"
            value={password}
            onChange={(event) => setPassword(event.target.value)}
            required
          />
        </div>
        <div>
          <label className="mb-2 block text-sm font-medium text-slate-200" htmlFor="photoURL">
            Profile image URL (optional)
          </label>
          <Input
            id="photoURL"
            type="url"
            placeholder="https://example.com/avatar.jpg"
            value={photoURL}
            onChange={(event) => setPhotoURL(event.target.value)}
          />
        </div>
        <Button type="submit" disabled={loading}>
          {loading ? 'Creating account...' : 'Create account'}
        </Button>
      </form>
    </Card>
  )
}
