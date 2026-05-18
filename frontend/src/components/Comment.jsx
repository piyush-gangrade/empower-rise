import React from 'react'

export default function Comment({ comment, me, editingId, editingText, setEditingText, startEdit, cancelEdit, onSave, onDelete }) {
  const isOwner = comment.user?.id === me?.id

  return (
    <li key={comment.id} style={{ padding: 12, borderBottom: '1px solid #eee' }}>
      <div style={{ display: 'flex', justifyContent: 'space-between', gap: 12 }}>
        <div>
          <strong>{comment.user?.username || comment.user?.name || 'Anonymous'}</strong>
          <div style={{ color: '#555', fontSize: 13 }}>{new Date(comment.createdAt || Date.now()).toLocaleString()}</div>
        </div>
        <div>
          {isOwner && (
            <>
              <button className="button button--small" onClick={() => startEdit(comment)}>Edit</button>
              <button className="button button--small" onClick={() => onDelete(comment.id)}>Delete</button>
            </>
          )}
        </div>
      </div>

      {editingId === comment.id ? (
        <form onSubmit={(e) => onSave(e)} style={{ marginTop: 8 }}>
          <textarea value={editingText} onChange={(e) => setEditingText(e.target.value)} rows={3} className="input" />
          <div style={{ display: 'flex', gap: 8, marginTop: 8 }}>
            <button className="button" type="submit">Save</button>
            <button className="button" type="button" onClick={cancelEdit}>Cancel</button>
          </div>
        </form>
      ) : (
        <p style={{ marginTop: 8 }}>{comment.text || comment.comment}</p>
      )}
    </li>
  )
}
