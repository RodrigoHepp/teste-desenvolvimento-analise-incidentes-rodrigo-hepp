import type { SelectHTMLAttributes } from 'react';
import type { UseFormRegisterReturn } from 'react-hook-form';
import '../styles/Select.css';

interface SelectProps extends SelectHTMLAttributes<HTMLSelectElement> {
  label?: string;
  error?: string;
  register?: UseFormRegisterReturn;
  options: { value: string; label: string }[];
}

export function Select({
  label,
  error,
  register,
  options,
  id,
  ...props
}: SelectProps) {
  return (
    <div className="form-group">
      {label && (
        <label htmlFor={id}>
          {label}
        </label>
      )}

      <select
        id={id}
        {...register}
        {...props}
        className={`select ${error ? 'error' : ''}`}
      >
        <option value="">Selecione uma opção</option>

        {options.map((opt) => (
          <option key={opt.value} value={opt.value}>
            {opt.label}
          </option>
        ))}
      </select>

      {error && (
        <span className="error-message">
          {error}
        </span>
      )}
    </div>
  );
}