export const parseJwt = (token) => {
  if (!token) return null
  try {
    const payload = token.split('.')[1]
    if (!payload) return null
    const base64 = payload.replace(/-/g, '+').replace(/_/g, '/')
    const binary = atob(base64)
    const jsonPayload = decodeURIComponent(
      Array.from(binary)
        .map((c) => `%${`00${c.charCodeAt(0).toString(16)}`.slice(-2)}`)
        .join(''),
    )
    return JSON.parse(jsonPayload)
  } catch (error) {
    console.error('Failed to parse JWT', error)
    return null
  }
}

export const getAuthToken = () => localStorage.getItem('authToken')
export const getAdminToken = () => localStorage.getItem('adminToken')

export const getTokenPayload = (token) => parseJwt(token)

export const getTokenExpiry = (token) => {
  const payload = getTokenPayload(token)
  if (!payload?.exp) return null
  const expires = Number(payload.exp)
  if (Number.isNaN(expires)) return null
  return expires * 1000
}

export const getTokenRole = (token) => getTokenPayload(token)?.role || null

export const isTokenExpired = (token) => {
  const expiry = getTokenExpiry(token)
  return expiry === null ? false : Date.now() > expiry
}

export const isAuthenticated = () => {
  const token = getAuthToken()
  return Boolean(token && !isTokenExpired(token))
}

export const isAdminAuthenticated = () => {
  const token = getAdminToken()
  if (!token || isTokenExpired(token)) return false
  const role = getTokenRole(token)
  return Boolean(role && role.toLowerCase() === 'admin')
}

export const saveAuthSession = ({ token, user, role, expiresAt }) => {
  localStorage.setItem('authToken', token)
  localStorage.setItem('authRole', role || getTokenRole(token) || 'User')
  if (user) localStorage.setItem('authUser', JSON.stringify(user))
  const expiry = expiresAt || getTokenExpiry(token)
  if (expiry) localStorage.setItem('authExpiry', String(expiry))
}

export const saveAdminSession = ({ token, user, role, expiresAt }) => {
  localStorage.setItem('adminToken', token)
  localStorage.setItem('adminRole', role || getTokenRole(token) || 'Admin')
  if (user) localStorage.setItem('adminUser', JSON.stringify(user))
  const expiry = expiresAt || getTokenExpiry(token)
  if (expiry) localStorage.setItem('adminExpiry', String(expiry))
}

export const logoutAuth = () => {
  localStorage.removeItem('authToken')
  localStorage.removeItem('authRole')
  localStorage.removeItem('authUser')
  localStorage.removeItem('authExpiry')
}

export const logoutAdmin = () => {
  localStorage.removeItem('adminToken')
  localStorage.removeItem('adminRole')
  localStorage.removeItem('adminUser')
  localStorage.removeItem('adminExpiry')
}

export const getAuthHeaders = () => {
  const token = getAuthToken()
  return token ? { Authorization: `Bearer ${token}` } : {}
}

export const getAdminHeaders = () => {
  const token = getAdminToken()
  return token ? { Authorization: `Bearer ${token}` } : {}
}

export const getAuthUser = () => {
  try {
    return JSON.parse(localStorage.getItem('authUser') || 'null') || null
  } catch {
    return null
  }
}

export const getAdminUser = () => {
  try {
    return JSON.parse(localStorage.getItem('adminUser') || 'null') || null
  } catch {
    return null
  }
}
