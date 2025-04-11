import dayjs from 'dayjs';
import { ICarro } from 'app/shared/model/carro.model';
import { ICliente } from 'app/shared/model/cliente.model';

export interface IRenta {
  id?: number;
  fechaInicio?: dayjs.Dayjs;
  fechaFin?: dayjs.Dayjs | null;
  precioTotal?: number;
  estado?: string;
  carro?: ICarro | null;
  cliente?: ICliente | null;
}

export const defaultValue: Readonly<IRenta> = {};
