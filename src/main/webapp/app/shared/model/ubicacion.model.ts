import dayjs from 'dayjs';
import { ICarro } from 'app/shared/model/carro.model';

export interface IUbicacion {
  id?: number;
  latitud?: number;
  longitud?: number;
  timestamp?: dayjs.Dayjs;
  carro?: ICarro | null;
}

export const defaultValue: Readonly<IUbicacion> = {};
