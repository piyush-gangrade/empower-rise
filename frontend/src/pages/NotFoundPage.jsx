import { Card } from '../components/ui/card.jsx'

export default function NotFoundPage() {
  return (
    <div className="mx-auto max-w-2xl">
      <Card title="Page not found" description="The route you are looking for does not exist.">
        <p className="text-slate-300">Use the navigation bar above to return to a valid section.</p>
      </Card>
    </div>
  )
}
