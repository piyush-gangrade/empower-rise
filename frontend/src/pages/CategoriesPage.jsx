import { Button } from '../components/ui/button.jsx'
import { Card } from '../components/ui/card.jsx'

export default function CategoriesPage() {
  return (
    <div className="space-y-6">
      <div className="flex flex-col gap-4 rounded-3xl border border-slate-800 bg-slate-900/80 p-6 shadow-glow sm:flex-row sm:items-center sm:justify-between">
        <div>
          <h1 className="text-3xl font-semibold text-white">Categories</h1>
          <p className="mt-2 text-sm text-slate-400">Create and manage categories used by blogs, funds, and donations.</p>
        </div>
        <Button>New category</Button>
      </div>
      <Card description="Category management pages will integrate /api/category endpoints.">
        <p className="text-slate-300">Display categories, search by name, and manage updates here.</p>
      </Card>
    </div>
  )
}
