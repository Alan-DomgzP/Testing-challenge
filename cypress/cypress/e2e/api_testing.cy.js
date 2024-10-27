describe('NFL Scoreboard API Tests', () => {
  
  const baseUrl = 'https://site.api.espn.com/apis/site/v2/sports/football/nfl/scoreboard';
  const year = 2023;

  // Test 1: Tenga resultado en la sección de eventos para la semana 1 a la 18 para el año 2023,
  //que cada registro cuente en competitors con 2 equipos, que cada equipo tenga un score
  //mayor o igual a 0
  it('Debe tener resultados en la sección de eventos para las semanas 1 a 18 del año 2023', () => {
    for (let week = 1; week <=18; week++) {
      cy.request(`${baseUrl}?dates=${year}&seasontype=2&week=${week}`).then((response) => {
        expect(response.status).to.equal(200);

        response.body.events.forEach(event => {
          expect(event).to.have.property('competitions');
          expect(event.competitions).to.have.length.greaterThan(0);

          const competition = event.competitions[0];
          expect(competition).to.have.property('competitors');
          expect(competition.competitors).to.have.length(2);

          competition.competitors.forEach(team => {
            expect(team).to.have.property('score');
            expect(parseInt(team.score)).to.be.at.least(0);
          });
        })
      })
    }
  })


  // Test Case 2: Verificar que cada equipo tenga 17 partidos al finalizar el año 2023
  it('Cada equipo debe tener 17 partidos al finalizar el año 2023', () => {
    const teamsPlayed = {};

    for (let week = 1; week <= 18; week++) {
      cy.request(`${baseUrl}?dates=${year}&seasontype=2&week=${week}`).then((response) => {
        const events = response.body.events;

        events.forEach(event => {
          expect(event).to.have.property('competitions');
          expect(event.competitions).to.have.length.greaterThan(0);

          const competition = event.competitions[0];
          expect(competition).to.have.property('competitors');
          expect(competition.competitors).to.have.length(2);

          competition.competitors.forEach(team => {
            const teamId = team.id;

            if (!teamsPlayed[teamId]) {
              teamsPlayed[teamId] = 0;
            }
            teamsPlayed[teamId]++;
          });
        });
      });
    }

    cy.then(() => {
      Object.entries(teamsPlayed).forEach(([teamId, gamesPlayed]) => {
        expect(gamesPlayed).to.equal(17, `El equipo ${teamId} debería haber jugado 17 partidos, pero jugó ${gamesPlayed}`);
      });

      console.log("Conteo de juegos por equipo:", teamsPlayed);
    });
  });


  // Test Case 3: Verificar que no existan eventos desde la semana 19
  it('No debe haber eventos para semanas mayores a 18 en el año 2023', () => {
    for (let week = 19; week <= 25; week++) {
        cy.request(`${baseUrl}?dates=${year}&seasontype=2&week=${week}`).then((response) => {
            expect(response.status).to.eq(200);

            const events = response.body.events;

            expect(events).to.exist;
            expect(events).to.be.an('array').that.is.empty;
        });
      }
  });


})
