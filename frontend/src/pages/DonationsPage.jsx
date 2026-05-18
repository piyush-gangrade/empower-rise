import { Button } from '../components/ui/button.jsx'
import { Card } from '../components/ui/card.jsx'

export default function DonationsPage() {
  return (
    <div className="space-y-6">
      <div className="flex flex-col gap-4 rounded-3xl border border-slate-800 bg-slate-900/80 p-6 shadow-glow sm:flex-row sm:items-center sm:justify-between">
        <div>
          <h1 className="text-3xl font-semibold text-white">Donations</h1>
          <p className="mt-2 text-sm text-slate-400">Track and manage donations and donation categories.</p>
        </div>
        <Button>New donation</Button>
      </div>
      <Card description="Donation records and category filters will connect with /api/v1/donation.">
        <p className="text-slate-300">Add donation creation and approval flows here.</p>
      </Card>
    </div>
  )
}
