import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Mantenimiento from './mantenimiento';
import MantenimientoDetail from './mantenimiento-detail';
import MantenimientoUpdate from './mantenimiento-update';
import MantenimientoDeleteDialog from './mantenimiento-delete-dialog';

const MantenimientoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Mantenimiento />} />
    <Route path="new" element={<MantenimientoUpdate />} />
    <Route path=":id">
      <Route index element={<MantenimientoDetail />} />
      <Route path="edit" element={<MantenimientoUpdate />} />
      <Route path="delete" element={<MantenimientoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MantenimientoRoutes;
