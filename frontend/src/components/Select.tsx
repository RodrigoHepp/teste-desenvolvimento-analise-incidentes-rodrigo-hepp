import { SelectHTMLAttributes } from 'react';
import { UseFormRegisterReturn } from 'react-hook-form';
import '../styles/Select.css';

interface SelectProps extends SelectHTMLAttributes<HTMLSelectElement> {
  label?: string;
  error?: string;
  register?: UseFormRegisterReturn;
  options: { value: string; label: string }[];
}

export function Select({ label, error, register, options, ...props }: SelectProps) {
  return (
    <div className="form-group">
      {label && <label htmlFor={props.id}>{label}</label>}
      <select {...props} {...register} className={`select ${error ? 'error' : ''}`}>
        <option value="">Selecione uma opção</option>
        {options.map((opt) => (
          <option key={opt.value} value={opt.value}>
            {opt.label}
          </option>
        ))}
      </select>
      {error && <span className="error-message">{error}</span>}
    </div>
  );
}
