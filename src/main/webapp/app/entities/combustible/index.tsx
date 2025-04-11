import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Combustible from './combustible';
import CombustibleDetail from './combustible-detail';
import CombustibleUpdate from './combustible-update';
import CombustibleDeleteDialog from './combustible-delete-dialog';

const CombustibleRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Combustible />} />
    <Route path="new" element={<CombustibleUpdate />} />
    <Route path=":id">
      <Route index element={<CombustibleDetail />} />
      <Route path="edit" element={<CombustibleUpdate />} />
      <Route path="delete" element={<CombustibleDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CombustibleRoutes;
