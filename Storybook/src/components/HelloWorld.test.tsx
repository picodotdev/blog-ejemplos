import React from 'react';
import ReactDOM from 'react-dom';

import { HelloWorld } from './HelloWorld';

it('has a h1 tag with text', () => {
  const div: HTMLDivElement = document.createElement('div');
  ReactDOM.render(
    <HelloWorld />,
    div
  );

  expect(div.querySelector('h1')).not.toBeNull();
  expect(div.querySelector('h1').textContent).toEqual('Hello World!');

  ReactDOM.unmountComponentAtNode(div);
});