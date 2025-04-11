import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Ubicacion from './ubicacion';
import UbicacionDetail from './ubicacion-detail';
import UbicacionUpdate from './ubicacion-update';
import UbicacionDeleteDialog from './ubicacion-delete-dialog';

const UbicacionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Ubicacion />} />
    <Route path="new" element={<UbicacionUpdate />} />
    <Route path=":id">
      <Route index element={<UbicacionDetail />} />
      <Route path="edit" element={<UbicacionUpdate />} />
      <Route path="delete" element={<UbicacionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UbicacionRoutes;
