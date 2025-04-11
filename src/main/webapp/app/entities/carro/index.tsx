import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Carro from './carro';
import CarroDetail from './carro-detail';
import CarroUpdate from './carro-update';
import CarroDeleteDialog from './carro-delete-dialog';

const CarroRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Carro />} />
    <Route path="new" element={<CarroUpdate />} />
    <Route path=":id">
      <Route index element={<CarroDetail />} />
      <Route path="edit" element={<CarroUpdate />} />
      <Route path="delete" element={<CarroDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CarroRoutes;
