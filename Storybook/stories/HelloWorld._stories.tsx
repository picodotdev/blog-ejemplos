import React from 'react';
import HelloWorld from '../src/components/HelloWorld';

export default {
  title: 'HelloWorld',
  parameters: {
    componentSubtitle: 'Componente básico de ejemplo',
  },
  component: HelloWorld,
};

export const HelloWorldStory = () => <HelloWorld />;
export const HelloNameStory = ({ name: String }) => <HelloWorld name="picodotdev"/>;

HelloWorldStory.story = {
  name: 'HelloWorld',
};

HelloNameStory.story = {
  name: 'AnotherHelloWorld',
};

