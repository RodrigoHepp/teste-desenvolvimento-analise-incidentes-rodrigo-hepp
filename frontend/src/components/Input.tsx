import { InputHTMLAttributes } from 'react';
import { UseFormRegisterReturn } from 'react-hook-form';
import '../styles/Input.css';

interface InputProps extends InputHTMLAttributes<HTMLInputElement> {
  label?: string;
  error?: string;
  register?: UseFormRegisterReturn;
}

export function Input({ label, error, register, ...props }: InputProps) {
  return (
    <div className="form-group">
      {label && <label htmlFor={props.id}>{label}</label>}
      <input {...props} {...register} className={`input ${error ? 'error' : ''}`} />
      {error && <span className="error-message">{error}</span>}
    </div>
  );
}
