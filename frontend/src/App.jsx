import { useEffect, useState } from "react";

export default function App() {
  const [chats, setChats] = useState([]);
  const [chiens, setChiens] = useState([]);
  const [classement, setClassement] = useState([]);
  const [resultat, setResultat] = useState(null);
  const [erreur, setErreur] = useState("");

  async function chargerChats() {
    const reponse = await fetch("/chat");
    setChats(await reponse.json());
  }

  async function chargerChiens() {
    const reponse = await fetch("/chien");
    setChiens(await reponse.json());
  }

  async function chargerClassement() {
    const reponse = await fetch("/classement");
    setClassement(await reponse.json());
  }

  useEffect(() => {
    chargerChats();
    chargerChiens();
    chargerClassement();
  }, []);

  async function ajouterChat(event) {
    event.preventDefault();
    const nom = event.target.nom.value;
    const vitesse = Number(event.target.vitesse.value);

    const reponse = await fetch("/chat", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ nom, vitesse }),
    });

    if (reponse.ok) {
      event.target.reset();
      chargerChats();
    } else {
      setErreur(await reponse.text());
    }
  }

  async function ajouterChien(event) {
    event.preventDefault();
    const nom = event.target.nom.value;
    const vitesse = Number(event.target.vitesse.value);

    const reponse = await fetch("/chien", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ nom, vitesse }),
    });

    if (reponse.ok) {
      event.target.reset();
      chargerChiens();
    } else {
      setErreur(await reponse.text());
    }
  }

  async function lancerCourse(event) {
    event.preventDefault();
    const chatId = event.target.chatId.value;
    const chienId = event.target.chienId.value;

    const reponse = await fetch(`/course?chatId=${chatId}&chienId=${chienId}`);

    if (reponse.ok) {
      setResultat(await reponse.json());
      setErreur("");
      chargerChats();
      chargerChiens();
      chargerClassement();
    } else {
      setErreur(await reponse.text());
    }
  }

  return (
    <>
      <header>
        <h1>Chat vs Chien</h1>
        <p>Course de sprint, 100 mètres. Crée des concurrents, choisis-en deux, lance la course.</p>
      </header>

      <main>
        <section className="panel-pair">
          <form className="panel panel--chat" onSubmit={ajouterChat}>
            <h2>Nouveau chat</h2>
            <label>
              Nom
              <input name="nom" required />
            </label>
            <label>
              Vitesse
              <input name="vitesse" type="number" required min="1" />
            </label>
            <button type="submit">Ajouter ce chat</button>
          </form>

          <form className="panel panel--chien" onSubmit={ajouterChien}>
            <h2>Nouveau chien</h2>
            <label>
              Nom
              <input name="nom" required />
            </label>
            <label>
              Vitesse
              <input name="vitesse" type="number" required min="1" />
            </label>
            <button type="submit">Ajouter ce chien</button>
          </form>
        </section>

        <section>
          <h2>Concurrents</h2>
          <div className="roster-pair">
            <div>
              <h3>Chats</h3>
              <ul className="roster-list">
                {chats.map((chat) => (
                  <li key={chat.id}>
                    {chat.name}
                    <span className="stats">
                      vitesse {chat.speed} · {chat.victoires} victoire(s)
                    </span>
                  </li>
                ))}
              </ul>
              {chats.length === 0 && <p className="empty-state">Aucun chat pour l'instant.</p>}
            </div>

            <div>
              <h3>Chiens</h3>
              <ul className="roster-list">
                {chiens.map((chien) => (
                  <li key={chien.id}>
                    {chien.name}
                    <span className="stats">
                      vitesse {chien.speed} · {chien.victoires} victoire(s)
                    </span>
                  </li>
                ))}
              </ul>
              {chiens.length === 0 && <p className="empty-state">Aucun chien pour l'instant.</p>}
            </div>
          </div>

          <form className="course-picker" onSubmit={lancerCourse}>
            <label>
              Chat
              <select name="chatId">
                {chats.map((chat) => (
                  <option key={chat.id} value={chat.id}>
                    {chat.name}
                  </option>
                ))}
              </select>
            </label>
            <label>
              Chien
              <select name="chienId">
                {chiens.map((chien) => (
                  <option key={chien.id} value={chien.id}>
                    {chien.name}
                  </option>
                ))}
              </select>
            </label>
            <button type="submit" disabled={chats.length === 0 || chiens.length === 0}>
              Lancer la course
            </button>
          </form>
          {erreur && <p className="feedback--erreur">{erreur}</p>}
        </section>

        {resultat && (
          <section className="section-piste">
            <h2>Résultat</h2>
            <p className="couloir couloir--chat">
              {resultat.coureurs[0].nom} : {resultat.coureurs[0].distance} m
            </p>
            <p className="couloir couloir--chien">
              {resultat.coureurs[1].nom} : {resultat.coureurs[1].distance} m
            </p>
            <p className="resultat-texte">
              {resultat.egaliteParfaite
                ? `Égalité parfaite après ${resultat.tours} tours !`
                : `${resultat.gagnant} gagne en ${resultat.tours} tours !`}
            </p>
          </section>
        )}

        <section>
          <h2>Classement</h2>
          <ol className="classement-list">
            {classement.map((animal) => (
              <li key={`${animal.espece}-${animal.id}`}>
                {animal.name}
                <span className="classement-espece">{animal.espece}</span>
                <span className="classement-victoires">{animal.victoires} victoire(s)</span>
              </li>
            ))}
          </ol>
          {classement.length === 0 && <p className="empty-state">Personne n'a encore couru.</p>}
        </section>
      </main>
    </>
  );
}
