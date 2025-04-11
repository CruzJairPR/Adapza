import carro from 'app/entities/carro/carro.reducer';
import cliente from 'app/entities/cliente/cliente.reducer';
import renta from 'app/entities/renta/renta.reducer';
import ubicacion from 'app/entities/ubicacion/ubicacion.reducer';
import combustible from 'app/entities/combustible/combustible.reducer';
import mantenimiento from 'app/entities/mantenimiento/mantenimiento.reducer';
import empleado from 'app/entities/empleado/empleado.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  carro,
  cliente,
  renta,
  ubicacion,
  combustible,
  mantenimiento,
  empleado,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
