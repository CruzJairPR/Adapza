export interface ICliente {
  id?: number;
  nombre?: string;
  apellido?: string;
  correo?: string;
  telefono?: string | null;
  direccion?: string | null;
}

export const defaultValue: Readonly<ICliente> = {};
