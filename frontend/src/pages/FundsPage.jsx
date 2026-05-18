import { Button } from '../components/ui/button.jsx'
import { Card } from '../components/ui/card.jsx'

export default function FundsPage() {
  return (
    <div className="space-y-6">
      <div className="flex flex-col gap-4 rounded-3xl border border-slate-800 bg-slate-900/80 p-6 shadow-glow sm:flex-row sm:items-center sm:justify-between">
        <div>
          <h1 className="text-3xl font-semibold text-white">Funds</h1>
          <p className="mt-2 text-sm text-slate-400">Manage funds, categories, and fund details.</p>
        </div>
        <Button>New fund</Button>
      </div>
      <Card description="Fund management will connect with /api/v1/fund endpoints and file uploads.">
        <p className="text-slate-300">Use this page to display fund goals, categories, and update flows.</p>
      </Card>
    </div>
  )
}
