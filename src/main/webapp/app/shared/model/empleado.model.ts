export interface IEmpleado {
  id?: number;
  nombre?: string;
  puesto?: string;
  correo?: string | null;
  telefono?: string | null;
}

export const defaultValue: Readonly<IEmpleado> = {};
