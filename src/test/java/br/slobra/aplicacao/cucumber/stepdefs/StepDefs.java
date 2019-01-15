package br.slobra.aplicacao.cucumber.stepdefs;

import br.slobra.aplicacao.ObrasApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = ObrasApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
