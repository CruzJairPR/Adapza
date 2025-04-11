import dayjs from 'dayjs';
import { ICarro } from 'app/shared/model/carro.model';

export interface ICombustible {
  id?: number;
  nivelActual?: number;
  tipo?: string | null;
  fechaRegistro?: dayjs.Dayjs;
  carro?: ICarro | null;
}

export const defaultValue: Readonly<ICombustible> = {};
