import { Button } from '../components/ui/button.jsx'
import { Card } from '../components/ui/card.jsx'

export default function BlogsPage() {
  return (
    <div className="space-y-6">
      <div className="flex flex-col gap-4 rounded-3xl border border-slate-800 bg-slate-900/80 p-6 shadow-glow sm:flex-row sm:items-center sm:justify-between">
        <div>
          <h1 className="text-3xl font-semibold text-white">Blogs</h1>
          <p className="mt-2 text-sm text-slate-400">View, create, edit, and delete blog posts.</p>
        </div>
        <Button>New blog</Button>
      </div>
      <Card description="Display blog posts, categories, and content management actions here.">
        <p className="text-slate-300">This page will connect to /api/v1/blogs endpoints.</p>
      </Card>
    </div>
  )
}
