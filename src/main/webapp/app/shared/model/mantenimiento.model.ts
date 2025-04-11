import dayjs from 'dayjs';
import { ICarro } from 'app/shared/model/carro.model';

export interface IMantenimiento {
  id?: number;
  tipo?: string;
  descripcion?: string | null;
  fecha?: dayjs.Dayjs;
  costo?: number | null;
  carro?: ICarro | null;
}

export const defaultValue: Readonly<IMantenimiento> = {};
