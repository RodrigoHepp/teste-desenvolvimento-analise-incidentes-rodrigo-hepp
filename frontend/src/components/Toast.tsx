import '../styles/Toast.css';

export interface ToastProps {
  message: string;
  type: 'success' | 'error' | 'info';
  visible: boolean;
}

export function Toast({ message, type, visible }: ToastProps) {
  if (!visible) return null;

  return <div className={`toast toast-${type}`}>{message}</div>;
}
