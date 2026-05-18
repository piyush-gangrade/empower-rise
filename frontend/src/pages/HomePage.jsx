import { Link } from 'react-router-dom'
import { Button } from '../components/ui/button.jsx'
import { Card } from '../components/ui/card.jsx'

const featuredFunds = [
  {
    title: 'Clean Water for Village',
    description: 'Help provide clean drinking water to remote communities.',
    raised: '$32,400',
    goal: '$50,000',
    progress: 65,
  },
  {
    title: 'Education for Girls',
    description: 'Fund scholarships and supplies for school-age children.',
    raised: '$18,700',
    goal: '$25,000',
    progress: 75,
  },
  {
    title: 'Health Care Outreach',
    description: 'Support mobile clinics and medical supplies in rural areas.',
    raised: '$12,900',
    goal: '$20,000',
    progress: 64,
  },
]

const recentDonations = [
  { name: 'Aisha', amount: '$120', target: 'Education' },
  { name: 'Mark', amount: '$60', target: 'Water' },
  { name: 'Priya', amount: '$240', target: 'Health' },
]

const blogPreviews = [
  { title: 'How to launch a successful fundraiser', category: 'Fundraising', date: 'May 12' },
  { title: 'Stories from recent campaigns', category: 'Impact', date: 'May 10' },
  { title: 'How donations create real change', category: 'Giving', date: 'May 8' },
]

const categories = ['Health', 'Education', 'Environment', 'Community', 'Emergency']

export default function HomePage() {
  return (
    <main className="home-page">
      <section className="hero-panel">
        <div className="hero-copy">
          <span className="eyebrow">Start a campaign</span>
          <h1>Raise funds for what matters most.</h1>
          <p>Discover meaningful campaigns, support urgent causes, or launch your own fundraiser in minutes.</p>
          <div className="hero-actions">
            <Button as={Link} to="/register" className="hero-button">
              Start Fundraiser
            </Button>
            <Button as={Link} to="/donations" variant="secondary" className="hero-button secondary">
              Donate Now
            </Button>
          </div>
          <div className="hero-stats">
            <Card className="hero-stat-card" title="Featured campaigns">
              <p>Browse our most impactful causes.</p>
            </Card>
            <Card className="hero-stat-card" title="Recent donations">
              <p>See the latest community support.</p>
            </Card>
            <Card className="hero-stat-card" title="Browse blogs">
              <p>Read stories and campaign tips.</p>
            </Card>
          </div>
        </div>

        <aside className="hero-panel-side">
          <div className="hero-card">
            <p className="tag">Trending campaign</p>
            <h2>Community food bank support</h2>
            <p>Join donors who are helping families access healthy food this month.</p>
            <div className="hero-card-metrics">
              <div>
                <span>Raised</span>
                <strong>$24,500</strong>
              </div>
              <div>
                <span>Goal</span>
                <strong>$30,000</strong>
              </div>
            </div>
          </div>
          <div className="hero-card-actions">
            <Button as={Link} to="/login">Login</Button>
            <Button as={Link} to="/admin-login" variant="ghost" className="ghost-button">
              Admin Login
            </Button>
          </div>
        </aside>
      </section>

      <section className="section-block" id="funds">
        <div className="section-header">
          <div>
            <p className="section-label">Explore Funds</p>
            <h2>Featured campaigns</h2>
          </div>
          <Button as={Link} to="/funds" variant="secondary" className="section-button">
            Browse all funds
          </Button>
        </div>
        <div className="card-grid">
          {featuredFunds.map((fund) => (
            <Card key={fund.title} className="fund-card">
              <p className="fund-label">Campaign</p>
              <h3>{fund.title}</h3>
              <p>{fund.description}</p>
              <div className="fund-progress">
                <div className="progress-bar">
                  <div className="progress-fill" style={{ width: `${fund.progress}%` }} />
                </div>
                <div className="progress-meta">
                  <span>{fund.progress}% funded</span>
                  <span>{fund.raised} / {fund.goal}</span>
                </div>
              </div>
              <Button as={Link} to="/funds" className="section-button">
                Donate
              </Button>
            </Card>
          ))}
        </div>
      </section>

      <section className="section-grid">
        <div className="section-left">
          <div className="info-panel">
            <div className="info-header">
              <div>
                <p className="section-label">Donate</p>
                <h2>Give to campaigns that inspire you</h2>
              </div>
              <span className="badge">Fast donation flow</span>
            </div>
            <p>Support the causes you care about and see how your gift makes an immediate impact.</p>
            <div className="feature-list">
              <div className="feature-card">
                <p>Top cause</p>
                <strong>Emergency relief</strong>
              </div>
              <div className="feature-card">
                <p>Trending</p>
                <strong>Education initiatives</strong>
              </div>
            </div>
            <Button as={Link} to="/donations" className="section-button">
              Donate Now
            </Button>
          </div>

          <div className="info-panel">
            <div className="info-header">
              <div>
                <p className="section-label">Recent donations</p>
                <h3>Community giving</h3>
              </div>
              <span className="small-badge">Live feed</span>
            </div>
            <div className="donation-list">
              {recentDonations.map((donation) => (
                <div key={donation.name} className="donation-item">
                  <p>
                    <strong>{donation.name}</strong> donated <strong>{donation.amount}</strong> to {donation.target}
                  </p>
                </div>
              ))}
            </div>
          </div>
        </div>

        <div className="section-right">
          <div className="info-panel">
            <p className="section-label">Blogs</p>
            <h2>Stories to inspire giving</h2>
            <div className="blog-list">
              {blogPreviews.map((blog) => (
                <div key={blog.title} className="blog-preview">
                  <p className="blog-category">{blog.category}</p>
                  <h3>{blog.title}</h3>
                  <p>{blog.date}</p>
                </div>
              ))}
            </div>
            <Button as={Link} to="/blogs" className="section-button">
              Read more stories
            </Button>
          </div>

          <div className="info-panel">
            <p className="section-label">Categories</p>
            <h2>Find causes by category</h2>
            <div className="category-list">
              {categories.map((category) => (
                <span key={category} className="category-pill">
                  {category}
                </span>
              ))}
            </div>
          </div>
        </div>
      </section>
    </main>
  )
}
