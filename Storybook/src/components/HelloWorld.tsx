import React from 'react';
import PropTypes from 'prop-types';

import './HelloWorld.less';

interface Props {
  name?: String;
}

class HelloWorld extends React.Component<Props> {
  public static defaultProps = {
    name: "World"
  };

  public static propTypes = {
    name: PropTypes.string,
  };

  render() {
    return <h1 className="helloworld-title--red">Hello {this.props.name}!</h1>;
  }
}

export { HelloWorld as HelloWorld };

/**
 * Componente sencillo de ejemplo.
 */
export default HelloWorld;
