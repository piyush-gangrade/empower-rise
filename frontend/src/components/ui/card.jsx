import { cn } from '../../lib/utils.jsx'

export function Card({ className, title, description, children }) {
  return (
    <section className={cn('card', className)}>
      {title && <h2 className="card__title">{title}</h2>}
      {description && <p className="card__description">{description}</p>}
      {children}
    </section>
  )
}
