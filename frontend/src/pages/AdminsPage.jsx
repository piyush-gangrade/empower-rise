import { Button } from '../components/ui/button.jsx'
import { Card } from '../components/ui/card.jsx'

export default function AdminsPage() {
  return (
    <div className="space-y-6">
      <div className="flex flex-col gap-4 rounded-3xl border border-slate-800 bg-slate-900/80 p-6 shadow-glow sm:flex-row sm:items-center sm:justify-between">
        <div>
          <h1 className="text-3xl font-semibold text-white">Admins</h1>
          <p className="mt-2 text-sm text-slate-400">Manage admins, active/inactive lists, and admin profiles.</p>
        </div>
        <Button>New admin</Button>
      </div>
      <Card description="Admin management pages will integrate /api/v1/admin endpoints with filters and update actions.">
        <p className="text-slate-300">Build active/inactive views and admin status controls here.</p>
      </Card>
    </div>
  )
}
