package fr.projet.declarationfrais.aspect;

import javax.persistence.PostUpdate;

import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import fr.projet.declarationfrais.model.Declaration;
import fr.projet.declarationfrais.model.Restauration;

@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.projet.declarationfrais.controller..*(..)) && !execution(* javax.persistence.EntityManager.*(..))")
    public void logPointcut() {
        // ...
    }

    @Before("logPointcut()") // message pour indiquer les méthodes appelées
    public void methodeAppelee(JoinPoint joinPoint) {
        System.out.println("\u001B[33m ~~~~~~~~~~~~~~~~ Méthode appelée: " + joinPoint.getSignature().toShortString()
                + " ~~~~~~~~~~~~~~~~ \033[0m");
    }

    @AfterReturning(pointcut = "logPointcut()", returning = "result") // message pour indiquer les méthodes réussies
    public void methodeReussie(JoinPoint joinPoint, Object result) {
        System.out.println("\033[32m ~~~~~~~~~~~~~~~~ Méthode " + joinPoint.getSignature().toShortString()
                + " terminée avec succès. ~~~~~~~~~~~~~~~~ \033[0m");
    }

    @AfterThrowing(pointcut = "logPointcut()", throwing = "exception") // message pour indiquer les méthodes échouées
    public void methodeEchouee(JoinPoint joinPoint, Throwable exception) {
        System.out.println("\033[31m ~~~~~~~~~~~~~~~~ Erreur lors de l'exécution de la méthode "
                + joinPoint.getSignature().toShortString() + " : " + exception.getMessage()
                + " ~~~~~~~~~~~~~~~~ \033[0m");
    }

    @After("execution(* javax.persistence.EntityManager.persist(..))")
    public void insertIntoAlerte(JoinPoint joinPoint) {
        if (joinPoint.getArgs().length > 0 && joinPoint.getArgs()[0] instanceof Declaration) {
            Declaration newEntity = (Declaration) joinPoint.getArgs()[0];

            System.out.println("");
            System.out.println(
                    "\u001B[35m /!\\ /!\\ /!\\ ATTENTION BDD: Un nouvel enregistrement a été ajouté à la base de données  /!\\ /!\\ /!\\\u001B[0m");

            if (newEntity.getMontant_hebergement().isEmpty()) {
                System.out.println("\u001B[35m Aucun frais d'hébergement. \u001B[0m");
            } else {
                System.out.println("\u001B[35m Montant Hébergement: \u001B[0m" + newEntity.getMontant_hebergement());
            }

            if (newEntity.getMontant_transport() != null) {
                System.out.println("\u001B[35m Montant Transport: \u001B[0m" + newEntity.getMontant_transport());
            }

            if (newEntity.getRestaurationList() != null && !newEntity.getRestaurationList().isEmpty()) {
                System.out.println("\u001B[35m Frais de Restauration: \u001B[0m");
                for (Restauration restauration : newEntity.getRestaurationList()) {
                    if (restauration.getMontant_resto() != null) {
                        System.out.println("\u001B[35m   - Montant Resto: \u001B[0m" + restauration.getMontant_resto());
                    }
                }
            } else {
                System.out.println("\u001B[35m Aucun frais de restauration. \u001B[0m");
            }

            System.out.println("\u001B[35m Total des frais: \u001B[0m" + newEntity.getTotalFrais());
            System.out.println("");
        }
    }

    private ThreadLocal<String> refDossier = new ThreadLocal<>();
    private ThreadLocal<String> user = new ThreadLocal<>();

    @Before("execution(* javax.persistence.EntityManager.merge(..)) && args(entity)")
    public void beforeUpdate(JoinPoint joinPoint, Object entity) {
        if (entity instanceof Declaration) {
            Declaration updatedEntity = (Declaration) entity;
            refDossier.set(updatedEntity.getRefDossier());
            user.set(updatedEntity.getUser());
        }
    }

    @After("execution(* javax.persistence.EntityManager.merge(..)) && args(entity)")
    public void afterUpdate(JoinPoint joinPoint, Object entity) {
        if (entity instanceof Declaration) {
            Declaration updatedEntity = (Declaration) entity;
            String newStatut = updatedEntity.getStatut();
            String oldStatut = updatedEntity.getOldStatut();

            System.out.println("");
            System.out.println(
                    "\u001B[35m /!\\ /!\\ /!\\ ATTENTION BDD: Un enregistrement a été modifié dans la base de données /!\\ /!\\ /!\\ \u001B[0m");
            System.out.println("\u001B[35m Reférence de la déclaration: \u001B[0m" + refDossier.get());
            System.out.println("\u001B[35m Email de l'user concerné: \u001B[0m" + user.get());
            System.out.println("\u001B[35m Ancien statut: \u001B[0m" + oldStatut);
            System.out.println("\u001B[35m Nouveau statut: \u001B[0m" + newStatut);
            System.out.println("");

            refDossier.remove();
            user.remove();
        }
    }

    // méthode qui envoit un message lors d'un problème dans une requête sur la bdd
    @AfterThrowing(pointcut = "execution(* javax.persistence.EntityManager.persist(..)) || execution(* javax.persistence.EntityManager.remove(..)) || execution(* javax.persistence.EntityManager.merge(..))", throwing = "exception")
    public void bddAlerteEchec(JoinPoint joinPoint, Throwable exception) {
        System.out.println("\u001B[31m ~~~~~~~~~~~~~~~~ Erreur lors de l'exécution de la méthode "
                + joinPoint.getSignature().toShortString() + " : " + exception.getMessage()
                + " ~~~~~~~~~~~~~~~~ \u001B[0m");
    }

}