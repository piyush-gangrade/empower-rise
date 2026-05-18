import { Link } from 'react-router-dom'

export default function FundCard({ fund }) {
  const progress = fund.progress || 0
  const raised = fund.raised || fund.raisedAmount || '$0'
  const goal = fund.goal || fund.goalAmount || '$0'

  return (
    <article className="card fund-card">
      <Link to={`/funds/${fund.id}`} className="fund-image-link">
        <div className="fund-image" style={{ backgroundImage: `url(${fund.coverImage || fund.photoURL || ''})` }} />
      </Link>
      <div>
        <h3 className="fund-card__title">{fund.title || fund.name}</h3>
        <p className="fund-card__desc">{fund.shortDescription || fund.description?.slice(0, 120) || ''}</p>

        <div className="progress-bar">
          <div className="progress-fill" style={{ width: `${progress}%` }} />
        </div>

        <div className="meta-row">
          <span>{progress}% funded</span>
          <span>{raised} / {goal}</span>
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
