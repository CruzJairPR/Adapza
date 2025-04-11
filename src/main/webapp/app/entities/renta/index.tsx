import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Renta from './renta';
import RentaDetail from './renta-detail';
import RentaUpdate from './renta-update';
import RentaDeleteDialog from './renta-delete-dialog';

const RentaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Renta />} />
    <Route path="new" element={<RentaUpdate />} />
    <Route path=":id">
      <Route index element={<RentaDetail />} />
      <Route path="edit" element={<RentaUpdate />} />
      <Route path="delete" element={<RentaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RentaRoutes;
