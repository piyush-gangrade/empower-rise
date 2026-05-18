import { forwardRef } from 'react'
import { cn } from '../../lib/utils.jsx'

const variantStyles = {
  default: 'button-default',
  secondary: 'button-secondary',
  ghost: 'button-ghost',
}

const sizeStyles = {
  default: 'button-medium',
  sm: 'button-small',
  lg: 'button-large',
}

const Button = forwardRef(
  (
    {
      className,
      variant = 'default',
      size = 'default',
      as: Component = 'button',
      type = 'button',
      ...props
    },
    ref,
  ) => {
    return (
      <Component
        ref={ref}
        type={Component === 'button' ? type : undefined}
        className={cn('button', variantStyles[variant], sizeStyles[size], className)}
        {...props}
      />
    )
  },
)

Button.displayName = 'Button'

export { Button }
