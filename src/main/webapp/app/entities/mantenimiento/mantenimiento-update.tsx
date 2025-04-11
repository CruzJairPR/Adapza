import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getCarros } from 'app/entities/carro/carro.reducer';
import { createEntity, getEntity, reset, updateEntity } from './mantenimiento.reducer';

export const MantenimientoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const carros = useAppSelector(state => state.carro.entities);
  const mantenimientoEntity = useAppSelector(state => state.mantenimiento.entity);
  const loading = useAppSelector(state => state.mantenimiento.loading);
  const updating = useAppSelector(state => state.mantenimiento.updating);
  const updateSuccess = useAppSelector(state => state.mantenimiento.updateSuccess);

  const handleClose = () => {
    navigate(`/mantenimiento${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCarros({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    values.fecha = convertDateTimeToServer(values.fecha);
    if (values.costo !== undefined && typeof values.costo !== 'number') {
      values.costo = Number(values.costo);
    }

    const entity = {
      ...mantenimientoEntity,
      ...values,
      carro: carros.find(it => it.id.toString() === values.carro?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          fecha: displayDefaultDateTime(),
        }
      : {
          ...mantenimientoEntity,
          fecha: convertDateTimeFromServer(mantenimientoEntity.fecha),
          carro: mantenimientoEntity?.carro?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rentaCarrosApp.mantenimiento.home.createOrEditLabel" data-cy="MantenimientoCreateUpdateHeading">
            <Translate contentKey="rentaCarrosApp.mantenimiento.home.createOrEditLabel">Create or edit a Mantenimiento</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="mantenimiento-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('rentaCarrosApp.mantenimiento.tipo')}
                id="mantenimiento-tipo"
                name="tipo"
                data-cy="tipo"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('rentaCarrosApp.mantenimiento.descripcion')}
                id="mantenimiento-descripcion"
                name="descripcion"
                data-cy="descripcion"
                type="text"
              />
              <ValidatedField
                label={translate('rentaCarrosApp.mantenimiento.fecha')}
                id="mantenimiento-fecha"
                name="fecha"
                data-cy="fecha"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('rentaCarrosApp.mantenimiento.costo')}
                id="mantenimiento-costo"
                name="costo"
                data-cy="costo"
                type="text"
              />
              <ValidatedField
                id="mantenimiento-carro"
                name="carro"
                data-cy="carro"
                label={translate('rentaCarrosApp.mantenimiento.carro')}
                type="select"
              >
                <option value="" key="0" />
                {carros
                  ? carros.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/mantenimiento" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default MantenimientoUpdate;
