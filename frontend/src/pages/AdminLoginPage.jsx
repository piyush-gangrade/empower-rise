import { Button } from '../components/ui/button.jsx'
import { Card } from '../components/ui/card.jsx'
import { Input } from '../components/ui/input.jsx'

export default function AdminLoginPage() {
  return (
    <Card title="Admin Login" description="Sign in as an administrator to manage campaigns and users.">
      <form className="space-y-4">
        <div>
          <label className="mb-2 block text-sm font-medium text-slate-200" htmlFor="email">
            Email
          </label>
          <Input id="email" type="email" placeholder="admin@example.com" />
        </div>
        <div>
          <label className="mb-2 block text-sm font-medium text-slate-200" htmlFor="password">
            Password
          </label>
          <Input id="password" type="password" placeholder="••••••••" />
        </div>
        <Button type="submit">Sign in</Button>
      </form>
    </Card>
  )
}
