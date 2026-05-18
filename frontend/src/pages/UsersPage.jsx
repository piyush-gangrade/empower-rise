import { Button } from '../components/ui/button.jsx'
import { Card } from '../components/ui/card.jsx'

export default function UsersPage() {
  return (
    <div className="space-y-6">
      <div className="flex flex-col gap-4 rounded-3xl border border-slate-800 bg-slate-900/80 p-6 shadow-glow sm:flex-row sm:items-center sm:justify-between">
        <div>
          <h1 className="text-3xl font-semibold text-white">Users</h1>
          <p className="mt-2 text-sm text-slate-400">List and manage users here.</p>
        </div>
        <Button>New user</Button>
      </div>
      <Card description="User list, pagination, search, update and delete actions will live here.">
        <p className="text-slate-300">Connect this page to /api/v1/User endpoints to display user records.</p>
      </Card>
    </div>
  )
}
