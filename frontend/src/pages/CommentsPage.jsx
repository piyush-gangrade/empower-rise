import { Button } from '../components/ui/button.jsx'
import { Card } from '../components/ui/card.jsx'

export default function CommentsPage() {
  return (
    <div className="space-y-6">
      <div className="flex flex-col gap-4 rounded-3xl border border-slate-800 bg-slate-900/80 p-6 shadow-glow sm:flex-row sm:items-center sm:justify-between">
        <div>
          <h1 className="text-3xl font-semibold text-white">Comments</h1>
          <p className="mt-2 text-sm text-slate-400">Browse comments by user, fund, donation, or combined filters.</p>
        </div>
        <Button>New comment</Button>
      </div>
      <Card description="Comment listing and filter controls will connect with /api/v1/comment endpoints.">
        <p className="text-slate-300">Add search and filter UI for comments by user, fund, donation, and combined parameters.</p>
      </Card>
    </div>
  )
}
