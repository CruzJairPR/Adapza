export interface ICarro {
  id?: number;
  marca?: string;
  modelo?: string;
  anio?: number;
  placas?: string;
  color?: string | null;
  tipo?: string | null;
  estado?: string;
  kilometraje?: number | null;
}

export const defaultValue: Readonly<ICarro> = {};
