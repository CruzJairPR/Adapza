import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getCarros } from 'app/entities/carro/carro.reducer';
import { createEntity, getEntity, updateEntity } from './ubicacion.reducer';

export const UbicacionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const carros = useAppSelector(state => state.carro.entities);
  const ubicacionEntity = useAppSelector(state => state.ubicacion.entity);
  const loading = useAppSelector(state => state.ubicacion.loading);
  const updating = useAppSelector(state => state.ubicacion.updating);
  const updateSuccess = useAppSelector(state => state.ubicacion.updateSuccess);

  const handleClose = () => {
    navigate('/ubicacion');
  };

  useEffect(() => {
    if (!isNew) {
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
    if (values.latitud !== undefined && typeof values.latitud !== 'number') {
      values.latitud = Number(values.latitud);
    }
    if (values.longitud !== undefined && typeof values.longitud !== 'number') {
      values.longitud = Number(values.longitud);
    }
    values.timestamp = convertDateTimeToServer(values.timestamp);

    const entity = {
      ...ubicacionEntity,
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
          timestamp: displayDefaultDateTime(),
        }
      : {
          ...ubicacionEntity,
          timestamp: convertDateTimeFromServer(ubicacionEntity.timestamp),
          carro: ubicacionEntity?.carro?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rentaCarrosApp.ubicacion.home.createOrEditLabel" data-cy="UbicacionCreateUpdateHeading">
            <Translate contentKey="rentaCarrosApp.ubicacion.home.createOrEditLabel">Create or edit a Ubicacion</Translate>
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
                  id="ubicacion-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('rentaCarrosApp.ubicacion.latitud')}
                id="ubicacion-latitud"
                name="latitud"
                data-cy="latitud"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('rentaCarrosApp.ubicacion.longitud')}
                id="ubicacion-longitud"
                name="longitud"
                data-cy="longitud"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('rentaCarrosApp.ubicacion.timestamp')}
                id="ubicacion-timestamp"
                name="timestamp"
                data-cy="timestamp"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="ubicacion-carro"
                name="carro"
                data-cy="carro"
                label={translate('rentaCarrosApp.ubicacion.carro')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/ubicacion" replace color="info">
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

export default UbicacionUpdate;
