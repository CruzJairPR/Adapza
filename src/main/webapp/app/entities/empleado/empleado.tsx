import React, { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './empleado.reducer';

export const Empleado = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const empleadoList = useAppSelector(state => state.empleado.entities);
  const loading = useAppSelector(state => state.empleado.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    }
    return order === ASC ? faSortUp : faSortDown;
  };

  return (
    <div>
      <h2 id="empleado-heading" data-cy="EmpleadoHeading">
        <Translate contentKey="rentaCarrosApp.empleado.home.title">Empleados</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="rentaCarrosApp.empleado.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/empleado/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="rentaCarrosApp.empleado.home.createLabel">Create new Empleado</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {empleadoList && empleadoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="rentaCarrosApp.empleado.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('nombre')}>
                  <Translate contentKey="rentaCarrosApp.empleado.nombre">Nombre</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('nombre')} />
                </th>
                <th className="hand" onClick={sort('puesto')}>
                  <Translate contentKey="rentaCarrosApp.empleado.puesto">Puesto</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('puesto')} />
                </th>
                <th className="hand" onClick={sort('correo')}>
                  <Translate contentKey="rentaCarrosApp.empleado.correo">Correo</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('correo')} />
                </th>
                <th className="hand" onClick={sort('telefono')}>
                  <Translate contentKey="rentaCarrosApp.empleado.telefono">Telefono</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('telefono')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {empleadoList.map((empleado, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/empleado/${empleado.id}`} color="link" size="sm">
                      {empleado.id}
                    </Button>
                  </td>
                  <td>{empleado.nombre}</td>
                  <td>{empleado.puesto}</td>
                  <td>{empleado.correo}</td>
                  <td>{empleado.telefono}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/empleado/${empleado.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/empleado/${empleado.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/empleado/${empleado.id}/delete`)}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="rentaCarrosApp.empleado.home.notFound">No Empleados found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Empleado;
