Swordfish Policy Support


Architecture

The ServiceResolver retrieves policies in a two-step way very similar to the retrieval of endpoints.

In a first step, a PolicyDefinitionProvider is queried for raw policy documents corresponding to consumer and provider of the present service invocation. The PolicyDefinitionProvider may get the policy documents from local storage or querying a remote registry.

The second step is the extraction of the policy from the policy document for which purpose the ServiceResolver invokes a suitable PolicyExtractor.

After retrieval of the consumer policy and all provider policies assigned to the desired provider, it invokes the PolicyProcessor for policy trading, which either returns an agreed policy for the first matching policy pair (consumer policy with first matching provider policy) or null if no policy matches.

The PolicyProcessor delegates the work to a policy intersector component which deals with the platform policies, while outside the PolicyProcessor Policy and related objects are wrapped as Swordfish objects in order to reduce dependencies from the platform used for policy handling.

The platform policy intersector currently only supports some basic policy assertions. However, it is planned to become extensible allowing registration of handlers for custom policy assertions and listeners of the policy matching process


Interfaces and Implementation Classes

Interfaces are found in bundle "org.eclipse.swordfish.api" in package "org.eclipse.swordfish.api.policy". Implementation classes are found in bundle "org.eclipse.swordfish.policies".


Triggering of Policy Retrieval and Matching

Policy retrieval and matching is triggered by the existence of a property in the MessageExchange with key PolicyConstants.POLICY_CONSUMER_NAME and a QName as value which identify the consumer policy for the PolicyDescriptionProvider.


Policy Storage in the current PolicyDefinitionProvider

The current PolicyDefinitionProvider stores its policy documents in an archive file resource in "policies/Policies.zip". The layout of the archive is that it has no internal directory structure and contains the policy files and a file named "policies.dir" which has the layout of a properties file. It has lines like "<policy file name> = <consumer or provider QName>" assigning a QName to each policy file name. Thus, when a query enters the PolicyDefinitionProvider returns the content of the zipped file which has assigned the QName sent in the query.

Sample of "policies.dir":

ConsumerPolicy.xml = {http://samples.swordfish.eclipse.org}consumer
FlightServiceProviderPolicyA.xml = {http://service.dynamic.samples.swordfish.eclipse.org/}FlightServiceImpl
FlightServiceProviderPolicyB.xml = {http://service.dynamic.samples.swordfish.eclipse.org/}FlightServiceImpl

In this sample there is one consumer policy defined and two provider policy. Thus, a query with the consumer QName will get the content of "ConsumerPolicy.xml" while a query with the FlightServiceImpl provider QName will get two policy definitions with the contents of "FlightServiceProviderPolicyA.xml" and "FlightServiceProviderPolicyB.xml".
