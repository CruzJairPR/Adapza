import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/carro">
        <Translate contentKey="global.menu.entities.carro" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/cliente">
        <Translate contentKey="global.menu.entities.cliente" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/renta">
        <Translate contentKey="global.menu.entities.renta" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/ubicacion">
        <Translate contentKey="global.menu.entities.ubicacion" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/combustible">
        <Translate contentKey="global.menu.entities.combustible" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/mantenimiento">
        <Translate contentKey="global.menu.entities.mantenimiento" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/empleado">
        <Translate contentKey="global.menu.entities.empleado" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
