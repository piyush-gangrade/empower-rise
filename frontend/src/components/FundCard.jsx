import { Link } from 'react-router-dom'

export default function FundCard({ fund }) {
  // 1. Map to the EXACT property names your Spring Boot backend sends
  const raised = fund.collectedAmount || 0;
  const goal = fund.amount || 0;

  // 2. The backend doesn't send a percentage, so calculate it here.
  // We use Math.min to cap the progress bar at 100% just in case a campaign is overfunded.
  const progress = goal > 0 ? Math.min(Math.round((raised / goal) * 100), 100) : 0;

  // 3. Your backend sends a String[] array called 'images'. Grab the first one.
  const coverImage = fund.images && fund.images.length > 0 ? fund.images[0] : '';

  return (
    <article className="card fund-card">
      <Link to={`/funds/${fund.id}`} className="fund-image-link">
        <div className="fund-image" style={{ backgroundImage: `url(${coverImage})` }} />
      </Link>
      <div>
        <h3 className="fund-card__title">{fund.title}</h3>
        <p className="fund-card__desc">{fund.description?.slice(0, 120)}...</p>

        <div className="progress-bar">
          <div className="progress-fill" style={{ width: `${progress}%` }} />
        </div>

        <div className="meta-row">
          <span>{progress}% funded</span>
          {/* Added toLocaleString() to give you nice commas for large numbers (e.g., $1,000) */}
          <span>${raised.toLocaleString()} / ${goal.toLocaleString()}</span>
        </div>

        <div style={{ marginTop: 12 }}>
          <Link to={`/funds/${fund.id}`} className="button">
            Donate
          </Link>
        </div>
      </div>
    </article>
  )
}