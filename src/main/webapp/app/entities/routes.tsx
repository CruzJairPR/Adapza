import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Carro from './carro';
import Cliente from './cliente';
import Renta from './renta';
import Ubicacion from './ubicacion';
import Combustible from './combustible';
import Mantenimiento from './mantenimiento';
import Empleado from './empleado';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="carro/*" element={<Carro />} />
        <Route path="cliente/*" element={<Cliente />} />
        <Route path="renta/*" element={<Renta />} />
        <Route path="ubicacion/*" element={<Ubicacion />} />
        <Route path="combustible/*" element={<Combustible />} />
        <Route path="mantenimiento/*" element={<Mantenimiento />} />
        <Route path="empleado/*" element={<Empleado />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
