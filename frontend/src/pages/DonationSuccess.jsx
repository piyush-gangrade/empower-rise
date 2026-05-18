import { useLocation, useNavigate } from 'react-router-dom'

export default function DonationSuccess() {
  const { state } = useLocation()
  const navigate = useNavigate()
  const id = state?.id
  const amount = state?.amount

  if (!id) {
    return (
      <div>
        <h2>No donation found</h2>
        <button className="button" onClick={() => navigate('/')}>Back to home</button>
      </div>
    )
  }

  return (
    <div>
      <h2>Thank you for your donation!</h2>
      <p>Your donation of <strong>{amount}</strong> was received.</p>
      <p>Receipt ID: <strong>{id}</strong></p>
      <div style={{ marginTop: 20 }}>
        <button className="button" onClick={() => navigate('/funds')}>Explore more campaigns</button>
      </div>
    </div>
  )
}
