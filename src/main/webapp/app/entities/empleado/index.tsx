import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Empleado from './empleado';
import EmpleadoDetail from './empleado-detail';
import EmpleadoUpdate from './empleado-update';
import EmpleadoDeleteDialog from './empleado-delete-dialog';

const EmpleadoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Empleado />} />
    <Route path="new" element={<EmpleadoUpdate />} />
    <Route path=":id">
      <Route index element={<EmpleadoDetail />} />
      <Route path="edit" element={<EmpleadoUpdate />} />
      <Route path="delete" element={<EmpleadoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EmpleadoRoutes;
